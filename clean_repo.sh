#!/bin/bash
script_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$script_path"

rm -r Java
rm log_files/log_file.txt

#data/settings_data/* is not cleaned
