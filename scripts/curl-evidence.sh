#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p evidence
base=${API_URL:-http://localhost:8080}
curl -fsS "$base/doctor" | python3 -m json.tool > evidence/curl-get-doctors.json
token=$(curl -fsS -H 'Content-Type: application/json' -d '{"email":"patient1@example.com","password":"ClinicDemo2026!"}' "$base/patient/login" | python3 -c 'import json,sys;print(json.load(sys.stdin)["token"])')
curl -fsS "$base/patient/1/patient/$token" | python3 -m json.tool > evidence/curl-patient-appointments.json
curl -fsS "$base/doctor/filter/null/am/Cardiology" | python3 -m json.tool > evidence/curl-doctors-specialty-time.json
echo 'Three curl requests completed successfully; JWT kept out of saved evidence.'
