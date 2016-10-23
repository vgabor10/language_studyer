#!/bin/bash

clear
grep --color=always -E "$1" example_sentences_depot.txt | awk '{ print length, $0 }' | sort -n -r | cut -d" " -f2-

