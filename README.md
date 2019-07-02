# FACTORIE Workshop README

This is a workshop on [FACTORIE](http://factorie.cs.umass.edu/index.html), a toolkit for deployable probabilistic modeling, implemented as a software library in Scala. It provides its users with a succinct language for creating relational factor graphs, estimating parameters and performing inference.

Please follow the setup instructions below.

To view the workshop, open [index.html](index.html).

## FACTORIE Workshop Setup Instructions

Please set up the workshop software before the workshop if you want to do the exercises during the workshop.

There are three parts, installing Java, if necessary, setting up the workshop, and setting up FACTORIE itself.

### Install Java

If you don't already have the Java Developer Kit (JDK), version 8 or later, installed, go to <http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html> to install it.

### Fetch the Workshop from GitHub

This workshop is [hosted on GitHub](https://github.com/deanwampler/factorie-workshop). You can "fetch" it one of two ways.

#### Clone from the GitHub Repo

If you are accustomed to "cloning" Git repos, use the following command to clone the repo, creating a local copy (or use a GUI Git tool for this purpose).

```bash
git clone https://github.com/deanwampler/factorie-workshop
```

As an alternative, on the [workshop page](https://github.com/deanwampler/factorie-workshop), in the column on the right-hand side of the page is a button labeled *Clone in Desktop*, that helps you clone the repo for use in the *GitHub Desktop* app.

#### Download a Zip of the Repo

On the [workshop page](https://github.com/deanwampler/factorie-workshop), in the column on the right-hand side of the page is button labeled *Download ZIP*. Download the zip file and expand it somewhere convenient.

### Setup the `data` Directory

Because of the size of the dataset used for the exercises, a compressed Zip file is provided, which you need to expand. In the root directory for the workshop, you should find the following file, `factorie-workshop-data.zip`.

Unzip the file in workshop's root directory. It will create a new directory named `data`. On *nix-like machines, you can run the `zip` command from the shell:

```bash
unzip factorie-workshop-data.zip
```

On Windows, use any tool for expanding Zip files. Make sure the output `data` directory goes into the root directory for the workshop.

Some of that data, the Enron email corpus classified as SPAM and HAM came from [this research project](http://www.aueb.gr/users/ion/data/enron-spam/).

### Install SBT

The common build tool for Scala projects is [SBT](https://www.scala-sbt.org/). Follow the [download instructions](https://www.scala-sbt.org/download.html) to install it. Make sure the executable, `sbt`, is on your command path.

### Run `sbt` to Setup the Workshop

Using a command window, change to the directory where you installed the workshop and run the following the `sbt` command.

```bash
sbt
```

After some information messages, you'll see the project SBT prompt:

```
sbt:FactorieWorkshop>
```

After the prompt, type `help`, to see brief information about `sbt` commands

Next, run the `compile` task, which will download dependencies (which is the `update` task), then compile the code (`compile` task). If we had _unit tests_, then running `test` instead would update, compile, then run the unit tests. It should finish with a success message after a lot of output.

```
sbt:FactorieWorkshop> compile
[info] Updating ...
...
[info] Compiling 4 Scala sources ...
[info] Done compiling.
[success] Total time: 15 s, completed ...
sbt:FactorieWorkshop>
```

We'll use the Scala interpreter (REPL - _read, eval, print loop_) from within `sbt` using the `console` task. The advantage of running the REPL here is that the Java `CLASSPATH` will be set up to include all the project library dependencies, like FACTORIE. Try it now, where I'm showing a possible session here, where `scala>` is the REPL prompt, so what appears after it are statements and commands (starting with `:` that I entered):

```
sbt:FactorieWorkshop> console
[info] Starting scala interpreter...
Welcome to Scala 2.11.12 (OpenJDK ...).
Type in expressions for evaluation. Or try :help.

scala> :help
All commands can be abbreviated, e.g., :he instead of :help.
:edit <id>|<line>        edit history
:help [command]          print this summary or command-specific help
:history [num]           show the history (optional num is commands to show)
:h? <string>             search the history
:imports [name name ...] show import history, identifying sources of names
:implicits [-v]          show the implicits in scope
:javap <path|class>      disassemble a file or class name
:line <id>|<line>        place line(s) at the end of history
:load <path>             interpret lines in a file
:paste [-raw] [path]     enter paste mode or paste a file
:power                   enable power user mode
:quit                    exit the interpreter
:replay [options]        reset the repl and replay all previous commands
:require <path>          add a jar to the classpath
:reset [options]         reset the repl to its initial state, forgetting all session entries
:save <path>             save replayable session to a file
:sh <command line>       run a shell command (result is implicitly => List[String])
:settings <options>      update compiler options, if possible; see reset
:silent                  disable/enable automatic printing of results
:type [-v] <expr>        display the type of an expression without evaluating it
:kind [-v] <expr>        display the kind of expression's type
:warnings                show the suppressed warnings from the most recent line which had any

scala> 3 + 4
res0: Int = 7

scala> :quit

[success] Total time: 104 s, completed Jul 2, 2019 2:23:21 PM
sbt:FactorieWorkshop> exit
[info] shutting down server]
$
```
Note that I used `:quit` to leave the REPL (note the colon `:`) and back at the SBT prompt, enter `exit` to quit SBT, taking me back to the `bash` prompt `$`. YOu can also use `control-d` to exit the REPL and exit SBT.

Now you're ready for the workshop. If you're impatient to learn more, look at the [Tutorials](http://factorie.cs.umass.edu/tutorials.html) on the FACTORIE site, which we'll follow.

### Setting Up FACTORIE

Setting up FACTORIE itself is not strictly necessary, as the previous steps downloaded a pre-built version that we'll use. However, it is useful for browsing the FACTORIE source code (which I found necessary when debugging the exercises) and if you decide to use FACTORIE after this workshop. See [factorie.cs.umass.edu/installation.html](http://factorie.cs.umass.edu/installation.html) for details.

## Notes

The workshop was written in [reveal.js](http://lab.hakim.se/reveal-js/).

## Licenses

This presentation, including the exercises, is Copyright (C) 2014-2019 Dean Wampler, http://polyglotprogramming.com, unless otherwise noted, under the [Creative Commons License](http://creativecommons.org/licenses/by/3.0/).

FACTORIE is Copyright (C) 2008-2017 University of Massachusetts
Amherst, Department of Computer Science, and is licensed under the
terms of the Apache License, Version 2.0 or (at your option)
any subsequent version.

Reveal.js is Copyright (C) 2013 Hakim El Hattab, http://hakim.se under the MIT license.

See the included license files.
