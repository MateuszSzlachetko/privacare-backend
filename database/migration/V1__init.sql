CREATE TABLE IF NOT EXISTS "news" (
                        "id" uuid PRIMARY KEY,
                        "creator_id" uuid,
                        "created_at" timestamp,
                        "title" varchar(32),
                        "content" text
);

CREATE TABLE IF NOT EXISTS "users" (
                         "id" uuid PRIMARY KEY,
                         "auth_id" uuid UNIQUE,
                         "created_at" timestamp,
                         "name" varchar,
                         "surname" varchar,
                         "pesel" char(11) UNIQUE,
                         "phone_number" varchar
);

CREATE TABLE IF NOT EXISTS "notes" (
                         "id" uuid PRIMARY KEY,
                         "creator_id" uuid,
                         "patient_id" uuid,
                         "created_at" timestamp,
                         "content" text
);

CREATE TABLE IF NOT EXISTS "tasks" (
                         "id" uuid PRIMARY KEY,
                         "creator_id" uuid,
                         "created_at" timestamp,
                         "content" varchar,
                         "category_id" int,
                         "state" varchar
);

CREATE TABLE IF NOT EXISTS "category" (
                            "id" int PRIMARY KEY,
                            "name" varchar UNIQUE
);

CREATE TABLE IF NOT EXISTS "appointments" (
                                "id" uuid PRIMARY KEY,
                                "creator_id" uuid,
                                "patient_id" uuid,
                                "starts_at" timestamp,
                                "finishes_at" timestamp,
                                "state" varchar
);

ALTER TABLE "news" ADD FOREIGN KEY ("creator_id") REFERENCES "users" ("id");

ALTER TABLE "notes" ADD FOREIGN KEY ("creator_id") REFERENCES "users" ("id");

ALTER TABLE "notes" ADD FOREIGN KEY ("patient_id") REFERENCES "users" ("id");

ALTER TABLE "tasks" ADD FOREIGN KEY ("creator_id") REFERENCES "users" ("id");

ALTER TABLE "tasks" ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "appointments" ADD FOREIGN KEY ("creator_id") REFERENCES "users" ("id");

ALTER TABLE "appointments" ADD FOREIGN KEY ("patient_id") REFERENCES "users" ("id");
