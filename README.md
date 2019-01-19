# clj-tvision – Turbo Vision, the Clojure way

This is a [re-frame](https://github.com/Day8/re-frame) app.

But it doesn’t use Reagent. Or React. In fact, it’s not a web app at all.

It is a console app. As in, a text-mode app that runs in a terminal. In Clojure. No ClojureScript involved whatsoever.

(OK, I lied. It doesn’t run in a _real_ terminal. It emulates one, in Swing, using [clojure-lanterna](https://github.com/AvramRobert/clojure-lanterna).)

Just look how beautifully re-frame’y the code is. I mean, look at `core.clj`. Don’t look at `magic.clj`, or dragons will come and eat you.

## What it does

All this app does is display a coloured rectangle in terminal. You can move it around with the cursor keys and toggle its color using the space bar. Fun!

## Caveats

It will stop working randomly. It will hang your REPL. It will throw exceptions. It will do unexpected things. It may cause armageddon of any scale, and I, the author, disclaim any and all responsibility for that.

Oh, did I mention that it uses an unofficial fork of clojure-lanterna?

## Usage

```clojure
(require 'tvision.core)
(tvision.core/run)
```

## Why?

I’m just playing around. Plus, it proves just how awesomely awesome re-frame is.

_[Love letter to Mike Thompson redacted out]_

## Author

clj-tvision was written by [Daniel Janus](http://danieljanus.pl).

## License

```
        DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004

 Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>

 Everyone is permitted to copy and distribute verbatim or modified
 copies of this license document, and changing it is allowed as long
 as the name is changed.

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION

  0. You just DO WHAT THE FUCK YOU WANT TO.
```
