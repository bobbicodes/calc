I just spent like 3 hours recreating this Khan Academy graph in Reagent Hiccup SVG. LaTeX numbers were pregenerated with MathJax's `tex2svg` package and then I just pasted the `path` strings into the code, rather than render them dynamically with KaTeX... I believe I might have had a good reason for this but there's also a good chance that I didn't.

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
