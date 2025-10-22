-- Table: public.tb_player

-- DROP TABLE IF EXISTS public.tb_player;

CREATE TABLE IF NOT EXISTS public.tb_player
(
    id uuid NOT NULL,
    age numeric(38,0) NOT NULL,
    assists numeric(38,0),
    favorite_player character varying(255) COLLATE pg_catalog."default",
    favorite_position character varying(255) COLLATE pg_catalog."default",
    favorite_team character varying(255) COLLATE pg_catalog."default",
    goals numeric(38,0),
    last_update_date timestamp(6) without time zone,
    most_played_position character varying(255) COLLATE pg_catalog."default",
    name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    rating numeric(38,0),
    register_date timestamp(6) without time zone,
    skill character varying[] COLLATE pg_catalog."default",
    CONSTRAINT tb_player_pkey PRIMARY KEY (id),
    CONSTRAINT tb_player_favorite_position_check CHECK (favorite_position::text = ANY (ARRAY['Goalkeeper'::character varying, 'LeftBack'::character varying, 'CenterBack'::character varying, 'RightBack'::character varying, 'DefensiveMidfielder'::character varying, 'CentralMidfielder'::character varying, 'RightMidfielder'::character varying, 'LeftMidfielder'::character varying, 'OffensiveMidfielder'::character varying, 'LeftWinger'::character varying, 'RightWinger'::character varying, 'AttackingMidfielder'::character varying, 'SecondStriker'::character varying, 'Striker'::character varying]::text[])),
    CONSTRAINT tb_player_most_played_position_check CHECK (most_played_position::text = ANY (ARRAY['Goalkeeper'::character varying, 'LeftBack'::character varying, 'CenterBack'::character varying, 'RightBack'::character varying, 'DefensiveMidfielder'::character varying, 'CentralMidfielder'::character varying, 'RightMidfielder'::character varying, 'LeftMidfielder'::character varying, 'OffensiveMidfielder'::character varying, 'LeftWinger'::character varying, 'RightWinger'::character varying, 'AttackingMidfielder'::character varying, 'SecondStriker'::character varying, 'Striker'::character varying]::text[]))
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tb_player
    OWNER to postgres;