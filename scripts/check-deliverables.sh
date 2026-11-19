#!/usr/bin/env bash
# Checks the non-code deliverables of the capstone (section 9).
# Usage: bash scripts/check-deliverables.sh          (full check before a release)
#        bash scripts/check-deliverables.sh --ci     (CI: tags and reflections are only warnings)
set -u
cd "$(dirname "$0")/.."

fail=0
ci=0
[ "${1:-}" = "--ci" ] && ci=1
ok()   { echo "  OK   $1"; }
bad()  { echo "  FAIL $1"; fail=1; }
soft() { if [ "$ci" -eq 1 ]; then echo "  WARN $1"; else bad "$1"; fi; }

echo "Deliverables check"

# README
if [ -f README.md ] && ! grep -qiE 'TODO|<placeholder>|lorem' README.md; then ok "README.md"; else bad "README.md missing or has placeholders"; fi
grep -q '```mermaid' README.md 2>/dev/null && ok "README architecture diagram" || bad "README has no mermaid diagram"

# Pattern register
reg=docs/PATTERN_REGISTER.md
if [ -f "$reg" ]; then
  rows=$(grep -cE '^\| *[0-9]+ *\|' "$reg")
  [ "$rows" -ge 8 ] && ok "register rows: $rows (>= 8)" || bad "register rows: $rows (< 8)"
  c=$(grep -cE '^\| *[0-9]+ *\|.*\(Creational\)' "$reg")
  s=$(grep -cE '^\| *[0-9]+ *\|.*\(Structural\)' "$reg")
  b=$(grep -cE '^\| *[0-9]+ *\|.*\(Behavioral\)' "$reg")
  [ "$c" -ge 2 ] && [ "$s" -ge 2 ] && [ "$b" -ge 3 ] && ok "categories C=$c S=$s B=$b" || bad "categories C=$c S=$s B=$b (need 2/2/3)"
  rej=$(grep -cE 'adr/[0-9]+-rejected' "$reg")
  [ "$rej" -ge 2 ] && ok "rejected patterns: $rej" || bad "rejected patterns: $rej (< 2)"
else
  bad "$reg missing"
fi

# ADRs
adr=$(ls docs/adr/*.md 2>/dev/null | wc -l)
[ "$adr" -ge 5 ] && ok "ADR count: $adr" || bad "ADR count: $adr (< 5)"

# Reflections (template does not count)
refl=$(ls docs/reflection-*.md 2>/dev/null | grep -v TEMPLATE | wc -l)
[ "$refl" -ge 1 ] && ok "reflections: $refl" || soft "no docs/reflection-<handle>.md yet"

# Release tags
tags=$(git tag -l 'v*' 2>/dev/null | wc -l)
[ "$tags" -ge 3 ] && ok "release tags: $tags" || soft "release tags: $tags (< 3)"
git tag -l 'v1.0' 2>/dev/null | grep -q v1.0 && ok "v1.0 tag" || soft "v1.0 tag missing"

[ "$fail" -eq 0 ] && echo "ALL GOOD" || echo "SOME CHECKS FAILED"
exit "$fail"
