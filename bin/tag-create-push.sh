#!/bin/bash
cat <<EOF
Creating git tag $@ and pushing to remote.

EOF

git tag $@
git push origin $@