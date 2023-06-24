#!/bin/bash
script_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$script_path"

if [ -d  Java ]
then
   rm -r Java
   echo "folder deleted: language_studier/Java"
fi

if [ -f  log_files/log_file.txt ]
then
   rm log_files/log_file.txt
   echo "file deleted: language_studier/log_files/log_file.txt"
fi

#data/settings_data/* is not cleaned
