CREATE EXTENSION postgres_fdw;
-- Me conecto a la base de datos general desde la base de datos del cliente.
CREATE SERVER foreign_server
        FOREIGN DATA WRAPPER postgres_fdw
        OPTIONS (host '127.0.0.1', port '5432', dbname 'general');
-- Mapeo los usuarios de las bases de datos, en este caso son los dos el mismo.
CREATE USER MAPPING FOR postgres
        SERVER foreign_server
        OPTIONS (user 'postgres', password ''); -- PONE TU CLAVE
-- Hago un import del schema de la base de datos remota (general) en el schema de la base de datos local (cliente1)
-- Esto pone a disposición todas las tablas de la base de datos remota en la local como una suerte de vista sobre la que ademas se puede hacer inserts, updates y deletes.
IMPORT FOREIGN SCHEMA public
        FROM SERVER foreign_server
	INTO public;

-- Función que se va a encargar de validar fks en foreign tables.
-- Function: public.fk_contraint_validation()
-- DROP FUNCTION public.fk_contraint_validation();
CREATE OR REPLACE FUNCTION public.fk_contraint_validation()
  RETURNS trigger AS
$BODY$
    DECLARE
	_count integer;
    BEGIN
	RAISE NOTICE 'funcando';
	CREATE TEMP TABLE t ON COMMIT DROP AS SELECT NEW.*;
        -- Check that empname and salary are given
	EXECUTE 'select count(1) from t where ' || quote_ident(TG_ARGV[0]) || ' in (select id from ' || quote_ident(TG_ARGV[1]) || ')'; 
        IF _count = 0 THEN
            RAISE EXCEPTION 'fk constraint value of field % does not exist in foreign table %', TG_ARGV[0], TG_ARGV[1];
        END IF;
        RETURN NEW;
    END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.fk_contraint_validation()
  OWNER TO postgres;

-- Table: public.ticket

-- DROP TABLE public.ticket;

CREATE TABLE public.ticket
(
  id bigint NOT NULL,
  producto character varying NOT NULL,
  id_empresa bigint, -- No le declaro una fk constraint porque no se puede relacionar con una foreign table. Declaro un trigger para validar la relación.
  CONSTRAINT pk_ticket PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.ticket
  OWNER TO postgres;

-- Trigger: fk_validation_ticket_empresa on public.ticket
-- DROP TRIGGER fk_validation_ticket_empresa ON public.ticket;
CREATE TRIGGER fk_validation_ticket_empresa
  BEFORE INSERT OR UPDATE OF id_empresa
  ON public.ticket
  FOR EACH ROW
  EXECUTE PROCEDURE public.fk_contraint_validation('id_empresa', 'empresa'); -- Ejecuta función que valida que el campo id_empresa se encuentre como id en la foreign table empresa.