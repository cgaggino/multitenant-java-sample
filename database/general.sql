-- Table: public.empresa
-- DROP TABLE public.empresa;
CREATE TABLE public.empresa
(
  id bigint NOT NULL,
  name character varying,
  tenant_id bigint NOT NULL,
  CONSTRAINT pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.empresa
  OWNER TO postgres;

-- Table: public.usuario
-- DROP TABLE public.usuario;
CREATE TABLE public.usuario
(
  id bigint NOT NULL,
  username character varying NOT NULL,
  password character varying NOT NULL,
  id_empresa bigint NOT NULL,
  CONSTRAINT pk_usuario PRIMARY KEY (id),
  CONSTRAINT fk_empresa FOREIGN KEY (id_empresa)
      REFERENCES public.empresa (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.usuario
  OWNER TO postgres;