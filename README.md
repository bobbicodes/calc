This project is a toy graphing calculator that evaluates arbitrary functions with Nextjournal's clojure-mode for Codemirror 6 and the Small Clojure Interpreter (sci).

![Screenshot](calc2.png)

### Development mode
```
npm install
npx shadow-cljs watch app
```
start a ClojureScript REPL
```
npx shadow-cljs browser-repl
```
### Building for production

```
npx shadow-cljs release app
```
