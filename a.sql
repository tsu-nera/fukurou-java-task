CREATE TABLE public.result
(
  input_num integer NOT NULL,
  salary integer,
  age integer,
  pref_id integer,
  business integer,
  health integer,
  pension integer,
  employment integer,
  total integer,
  input_time timestamp with time zone,
  CONSTRAINT result_pkey PRIMARY KEY (input_num)
)

CREATE TABLE public.list
(
  num integer NOT NULL,
  rank_h integer,
  rank_p integer,
  max_salary integer,
  st_h integer,
  st_p integer,
  CONSTRAINT "standardSocialRankList_pkey" PRIMARY KEY (num)
)

CREATE TABLE public.info
(
  id integer NOT NULL,
  name character(5) NOT NULL,
  rate numeric(5,4) NOT NULL,
  CONSTRAINT info_pkey PRIMARY KEY (id)
)



REATE TABLE public.business_master
(
  business_id integer NOT NULL,
  occupations character(12),
  CONSTRAINT business_master_pkey PRIMARY KEY (business_id)
)

CREATE TABLE public.age_master
(
  age_id integer NOT NULL,
  age_division character(10),
  CONSTRAINT age_master_pkey PRIMARY KEY (age_id)
)