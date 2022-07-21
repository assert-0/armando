#!/bin/bash
cd repo

git config --global user.name "Mindsmiths commit bot"
git config --global user.email "infrastructure@vingd.com"

git add services/mitems/data.json
git commit -m "update data.json"
git push -o ci.skip
