#! /bin/bash

forge reset

for d in services/*/ ; do
    target="$d/target"
    if [[ -e $target && -d $target ]]; then
        echo "Deleting $target"
        rm -rf "$target"
    fi

    clientTarget="$d/clients/java/target"
    if [[ -e $clientTarget && -d $clientTarget ]]; then
        echo "Deleting $clientTarget"
        rm -rf "$clientTarget"
    fi
done

forge init && forge run

pkill forge && pkill java
