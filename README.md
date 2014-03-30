
# FACTORIE Workshop README

Please follow the setup instructions below.

To view the workshop, open [index.html](index.html).

## FACTORIE Workshop Setup Instructions

Please set up the workshop software before the workshop if you want to do the exercises during the workshop.

There are three parts, installing Java, if necessary, setting up the workshop, and setting up FACTORIE itself.

### Install Java

If you don't already have the Java Developer Kit (JDK), versions 6, 7, or 8, installed, go to <http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html> to install it.

### Fetch the Workshop from GitHub

This workshop is [hosted on GitHub](https://github.com/deanwampler/factorie-workshop). You can "fetch" it one of two ways.

#### Clone from the GitHub Repo

If you are accustomed to "cloning" Git repos, use the following command to clone the repo, creating a local copy (or use a GUI Git tool for this purpose).

```
git clone https://github.com/deanwampler/factorie-workshop
```

As an alternative, on the [workshop page](https://github.com/deanwampler/factorie-workshop), in the column on the right-hand side of the page is a button labeled *Clone in Desktop*, that helps you clone the repo for use in the *GitHub Desktop* app. 

#### Download a Zip of the Repo

On the [workshop page](https://github.com/deanwampler/factorie-workshop), in the column on the right-hand side of the page is button labeled *Download ZIP*. Download the zip file and expand it somewhere convenient.

### Setup the `data` Directory

Because of the size of the dataset used for the exercises, a compressed Zip file is provided, which you need to expand. In the root directory for the workshop, you should find the following file, `factorie-workshop-data.zip`.

Unzip that file in this directory. It will create a new directory named `data`. On *nix-like machines, you can run the `zip` command from the shell:

```shell
unzip factorie-workshop-data.zip
```

On Windows, use any tool for expanding Zip files. Make sure the output `data` directory goes into the root directory for the workshop.

### Run `sbt` to Setup the Workshop

The repo includes the *de-facto* standard build tool for Scala, [sbt](http://www.scala-sbt.org/), which we'll use to download a pre-built copy of FACTORIE and work with it.

Using the same command window, change to the directory where you installed the workshop and run the following command. 

For *nix systems:

```
./sbt
```

For Windows:

```
.\sbt
```

After some information messages, you'll see this prompt:

```
FactorieWorkshop (master) 0.1>
```

It shows the name of the workshop's `sbt` "project" (`FactorieWorkshop`), the git branch (`master`), and the workshop version (`0.1`).

After the prompt, type `help`, to see brief information about `sbt` commands, then the `update` command. You'll need to be connected to the Internet for `update`, as it will find and download the software we need, such as a pre-built version of FACTORIE.

The `update` task may take several minutes. It should finish with a "success" message like this:

```
[success] Total time: 2 s, completed Mar 25, 2014 10:06:41 PM
FactorieWorkshop (master) 0.1>
```

You don't need this now, but you can run the Scala REPL from within `sbt` using the `console` task. The advantage of running the REPL here is that the Java "classpath" will be set up to include all the project library dependencies, like FACTORIE. You can exit the REPL using `:exit` as before.

To exit `sbt`, type `quit` at the prompt.

### Setting Up FACTORIE

Setting up FACTORIE itself is not strictly necessary, as the previous steps downloaded a pre-built version that we'll use. However, it is useful for browsing the FACTORIE source code (which I found necessary when debugging the exercises) and if you decide to use FACTORIE after this workshop. So, this section is optional.

Follow the instructions here for getting the source code from GitHub: [factorie.cs.umass.edu/installation.html](http://factorie.cs.umass.edu/installation.html). The instructions also provide links to pre-built versions.

After following the steps to clone the code from GitHub and after you have "checked out" (switched to) the `factorie-1.0.0-RC1` branch, run the following commands from a command prompt to complete the setup. If you are on Windows, change the `/` to `\`:

```
./sbt
```

This starts the Scala build tool, SBT, in an interpreter mode.  At the SBT prompt (a `>`), run these commands:

```
> compile
> test
```

The `compile` command will download additional dependent libraries and compile FACTORIE. The `test` command runs through the automated tests, which should complete with reporting errors.

> **NOTE:** If you run into problems compiling or testing FACTORIE, don't worry. We'll use the prebuilt version in the workshop.

If the code compiled successfully (whether or not the tests executed successfully), let's do one final test to make sure everything is ready to go. Type the following command:

```
> console
```

This command starts an interactive interpreter, called a REPL (read, evaluate, print loop) for the [Scala programming language](http://scala-lang.org), which is used to write FACTORIE applications. We'll cover the basics of Scala syntax as we go. For now, note that it is a newer alternative to Java that runs on the Java Virtual Machine.

You should see the following messages printed. Your version numbers may be slightly different:

```
[info] Starting scala interpreter...
[info]
Welcome to Scala version 2.10.1 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_45).
Type in expressions to have them evaluated.
Type :help for more information.

scala>
```

At the `scala>` prompt, type a simple expression like `3 + 4`. It will answer `7`. 

You can play with the REPL if you want. When you're done, enter `:quit` to leave the Scala REPL (note the colon `:`) and back at the SBT prompt, enter `exit` to quit SBT.

Now you're ready for the workshop. If you're impatient to learn more, look at the [Tutorials](http://factorie.cs.umass.edu/tutorials.html) on the FACTORIE site, which we'll follow.


## Notes

The workshop was written in [reveal.js](http://lab.hakim.se/reveal-js/).

## Licenses

This presentation, including the exercises, is Copyright (C) 2014 Dean Wampler, http://polyglotprogramming.com, unless otherwise noted, under the [Creative Commons License](http://creativecommons.org/licenses/by/3.0/).

FACTORIE is Copyright (C) 2008-2010 University of Massachusetts
Amherst, Department of Computer Science, and is licensed under the
terms of the Apache License, Version 2.0 or (at your option)
any subsequent version.

Reveal.js is Copyright (C) 2013 Hakim El Hattab, http://hakim.se under the MIT license.

See the included license files.
