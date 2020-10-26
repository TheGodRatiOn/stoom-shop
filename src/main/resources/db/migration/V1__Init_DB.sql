CREATE DATABASE stoom
    WITH
    OWNER = postgres
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE public.game
(
   id character(36) COLLATE pg_catalog."default" NOT NULL,
   name character(50) COLLATE pg_catalog."default",
   review_fk character(36) COLLATE pg_catalog."default",
   url text COLLATE pg_catalog."default",
   price integer,
   CONSTRAINT game_pkey PRIMARY KEY (id)

)

TABLESPACE pg_default;

ALTER TABLE public.game
   OWNER to postgres;

CREATE TABLE public.message
(
    id character(36) COLLATE pg_catalog."default" NOT NULL,
    user_reciever_fk character(36) COLLATE pg_catalog."default",
    text_m text COLLATE pg_catalog."default",
    time_date timestamp without time zone,
    user_sender_fk character(36) COLLATE pg_catalog."default",
    CONSTRAINT message_pkey PRIMARY KEY (id),
    )

TABLESPACE pg_default;

ALTER TABLE public.message
    OWNER to postgres;

CREATE TABLE public.review
(
    text_review text COLLATE pg_catalog."default",
    asessment integer,
    id character(36) COLLATE pg_catalog."default" NOT NULL,
    user_fk character(36) COLLATE pg_catalog."default",
    game_fk character(36) COLLATE pg_catalog."default",
    CONSTRAINT review_pkey PRIMARY KEY (id),
)

TABLESPACE pg_default;

ALTER TABLE public.review
    OWNER to postgres;

CREATE TABLE public.user
(
    id character(36) COLLATE pg_catalog."default" NOT NULL,
    name character(20) COLLATE pg_catalog."default",
    role character(10) COLLATE pg_catalog."default",
    review_fk character(36) COLLATE pg_catalog."default",
    CONSTRAINT user_pkey PRIMARY KEY (id),
    )

TABLESPACE pg_default;

ALTER TABLE public.user
    OWNER to postgres;

ALTER TABLE public.game
    ADD CONSTRAINT g_r FOREIGN KEY (review_fk)
        REFERENCES public.review (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE public.message
    ADD CONSTRAINT m_u_r FOREIGN KEY (user_reciever_fk)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

   ALTER TABLE public.message
    ADD CONSTRAINT m_u_s FOREIGN KEY (user_sender_fk)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE public.review
    ADD CONSTRAINT r_g FOREIGN KEY (game_fk)
        REFERENCES public.game (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE public.review
    ADD CONSTRAINT r_u FOREIGN KEY (user_fk)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE public.user
    ADD CONSTRAINT u_r FOREIGN KEY (review_fk)
        REFERENCES public.review (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

CREATE TABLE public.game_user
(
    game_fk character(36) COLLATE pg_catalog."default" NOT NULL,
    user_fk character(36) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT game_user_pkey PRIMARY KEY (game_fk, user_fk),
    CONSTRAINT g_u_g FOREIGN KEY (game_fk)
        REFERENCES public.game (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT g_u_u FOREIGN KEY (user_fk)
        REFERENCES public.user (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.game_user
    OWNER to postgres;
