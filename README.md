# rme-web

* https://github.com/antlr

`antlr4 -o /data/perso/dev/rme-web/app/grammars/tsql -package grammars.tsql -Dlanguage=Java -listener -encoding utf-8 -visitor -lib /data/perso/dev/rme-web/app/grammars/tsql /data/perso/dev/rme-web/app/grammars/tsql/TSqlParser.g4 /data/perso/dev/rme-web/app/grammars/tsql/TSqlLexer.g4`