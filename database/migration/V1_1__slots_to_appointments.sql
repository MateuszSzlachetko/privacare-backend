CREATE TABLE IF NOT EXISTS "slot" (
                                      "id" uuid PRIMARY KEY,
                                      "doctor_id" uuid,
                                      "starts_at" timestamp,
                                      "reserved" bool
);

ALTER TABLE "slot" ADD FOREIGN KEY ("doctor_id") REFERENCES "user" ("id");

ALTER TABLE "appointment" DROP COLUMN IF EXISTS "starts_at";

ALTER TABLE "appointment" DROP COLUMN IF EXISTS "finishes_at";

ALTER TABLE "appointment" DROP COLUMN IF EXISTS "state";

ALTER TABLE "appointment" ADD COLUMN IF NOT EXISTS "slot_id" uuid UNIQUE;

ALTER TABLE "appointment" ADD FOREIGN KEY ("slot_id") REFERENCES "slot" ("id");
