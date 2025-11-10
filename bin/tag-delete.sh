#!/bin/bash
cat <<EOF
Delete local and remote git tag $@

EOF

git tag -d $@
git push origin --delete $@