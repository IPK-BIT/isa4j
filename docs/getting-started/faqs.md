---
title: FAQs
nav_order: 3
parent: Getting Started
---
# Frequently Asked Questions

## Table of contents
{: .no_toc .text-delta }

1. TOC
{:toc}

---

## Any Things to Look Out For?

- All Strings passed to isa4J will be sanitized to make sure they don't break the format. Practically that means all TABs and ENTERs will be converted to spaces. Watch out for that if you're passing data from somewhere into isa4J and then parsing the files somewhere else back into a database: The result will not be the same as the initial input.

## Does isa4J support ISA JSON?

isa4J does not provide export functions to the ISA JSON format.
If you need ISA JSON files, you can create ISATab files with isa4J and then [convert them with the python API](https://isatools.readthedocs.io/en/latest/conversions.html).
The conversion is very straightforward and easy to do even if you have no experience working in python.

## Why is my Study/Assay File empty?
Perhaps you forgot to close it? isa4J uses an OutputStreamWriter under the hood so if you don't close your file (or don't release your stream) your ISA-Tab contents might just still be in the internal buffer.
