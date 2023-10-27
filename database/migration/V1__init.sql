CREATE TABLE IF NOT EXISTS "news" (
                        "id" uuid PRIMARY KEY,
                        "creator_id" uuid,
                        "created_at" timestamp,
                        "title" varchar(64),
                        "content" text
);

CREATE TABLE IF NOT EXISTS "user" (
                         "id" uuid PRIMARY KEY,
                         "auth_id" uuid UNIQUE,
                         "created_at" timestamp,
                         "name" varchar,
                         "surname" varchar,
                         "pesel" char(11) UNIQUE,
                         "phone_number" varchar
);

CREATE TABLE IF NOT EXISTS "note" (
                         "id" uuid PRIMARY KEY,
                         "creator_id" uuid,
                         "patient_id" uuid,
                         "created_at" timestamp,
                         "content" text
);

CREATE TABLE IF NOT EXISTS "task" (
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

CREATE TABLE IF NOT EXISTS "appointment" (
                                "id" uuid PRIMARY KEY,
                                "creator_id" uuid,
                                "patient_id" uuid,
                                "starts_at" timestamp,
                                "finishes_at" timestamp,
                                "state" varchar
);

ALTER TABLE "news" ADD FOREIGN KEY ("creator_id") REFERENCES "user" ("id");

ALTER TABLE "note" ADD FOREIGN KEY ("creator_id") REFERENCES "user" ("id");

ALTER TABLE "note" ADD FOREIGN KEY ("patient_id") REFERENCES "user" ("id");

ALTER TABLE "task" ADD FOREIGN KEY ("creator_id") REFERENCES "user" ("id");

ALTER TABLE "task" ADD FOREIGN KEY ("category_id") REFERENCES "category" ("id");

ALTER TABLE "appointment" ADD FOREIGN KEY ("creator_id") REFERENCES "user" ("id");

ALTER TABLE "appointment" ADD FOREIGN KEY ("patient_id") REFERENCES "user" ("id");
