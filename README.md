# Bash war gestern: Shell Scripting mit Babashka

Wann hast du das letzte Mal ein etwas komplexeres Bash Script geschrieben und
dir gewünscht, du könntest das Ding einfach mit Java schreiben? Java können wir
dir zwar nicht direkt anbieten, dafür aber Clojure. Babashka ist eine
Script-Engine, mit der wir quasi mit Clojure Shell-Skripte und Tasks schreiben
können und die Dank der nativen Compilation mit der Graal VM eine Startup Zeit
hat, die an native Bash-Skripte herankommt.

In unserem Workshop zeigen wir dir, wie du mit Babashka die etwas komplizierten
Shell Probleme lösen kannst und wenden das dann auch gleich an.

## Ablauf

In diesem Repository befinden sich verschiedene Skripte, die Stück für Stück
aufgerufen und absolviert werden können. Viele Skripte dienen nur einem ersten
Eindruck. Hier ist eure Kreativität gefragt 🧑‍🎨 Wandelt die Aufgaben ab, denkt
weiter, experimentiert herum. Es geht hier nicht um das Lösen der Aufgaben,
sondern um das gemeinsame Lernen und Verstehen.

Fangen wir zuerst damit an den Editor einzurichten und starten dann mit den
Scripts im Ordner `scripts`.

## Voraussetzungen

* babashka ([Quickstart](https://github.com/babashka/babashka#quickstart), [Installation](https://github.com/babashka/babashka#installation))
* Editor, wir werden mit VS Code arbeiten
  * [VSCode herunterladen](https://code.visualstudio.com/)
  * [Calva Plugin installieren](https://marketplace.visualstudio.com/items?itemName=betterthantomorrow.calva)

## Editor einrichten

Wir verwenden hier VS Code bzw. VS Codium mit dem Plugin "Calva" (links weiter
unten). Dann starten wir eine REPL und verbinden den Editor damit. Das klappt
besonders gut mit entsprechend konfiguriertem Editor, weshalb wir uns hier kurz
die Zeit nehmen.

Hier ist ein kleines Video, um in VSCode eine Babashka REPL zu starten und den
Editor damit zu verbinden. Dann können wir den Code direkt aus dem Editor in die
REPL schicken.

https://user-images.githubusercontent.com/1507474/158079767-1ce0f306-5c46-4d72-b284-29fa089d153d.mp4


### Tastenkürzel Empfehlungen

Besonders grandios ist es wenn man eine REPL hat aus dem Editor heraus damit
interagieren zu können. Dafür gibt es ein paar Tastenkürzel, die ihr nach eurem
Belieben konfigurieren könnt. Das hier sind meine (Christian) Empfehlungen:

* `C-c C-c` (Control C, Control C): Evaluiert S-Expression (schickt also den
aktuellen Code vom Cursor in die REPL.)
* `C-c C-k`: Lädt den gesamten Code aus der Datei in die REPL.
* `C-c right`: Schiebt die schließende Klammer einen Ausdruck weiter nach rechts.
* `C-c left`: Schiebt die schließende Klammer einen Ausdruck zurück nach links.
* `C-c C-d`: Hat man aus Versehen eine Endlosschleife erzeugt, bricht man den letzten Ausdruck mit diesem Kürzel wieder ab.

```json
[
    {
        "key": "ctrl+c ctrl+c",
        "command": "calva.evaluateCurrentTopLevelForm",
        "when": "calva:connected"
    },
    {
        "key": "ctrl+c ctrl+d",
        "command": "calva.interruptAllEvaluations",
        "when": "calva:connected"
    },
    {
        "key": "ctrl+c ctrl+k",
        "command": "calva.loadFile",
        "when": "calva:connected"
    },
    {
        "key": "ctrl+c right",
        "command": "paredit.slurpSexpForward",
        "when": "calva:keybindingsEnabled && editorTextFocus && editorLangId == 'clojure' && editorLangId == 'clojure' && paredit:keyMap =~ /original|strict/"
    },
    {
        "key": "ctrl+c left",
        "command": "paredit.barfSexpForward",
        "when": "calva:keybindingsEnabled && editorTextFocus && editorLangId == 'clojure' && editorLangId == 'clojure' && paredit:keyMap =~ /original|strict/"
    },
]
```


## Team

* [@n2o](https://github.com/n2o), Christian Meter, Clojure UG Düsseldorf, Twitter: [cmeter_](https://twitter.com/cmeter_)
* [@bendisposto](https://github.com/bendisposto), Jens Bendisposto, rheinjug, Twitter: [jbendisposto](https://twitter.com/jbendisposto)
* [@ebbinghaus](https://github.com/MrEbbinghaus), Björn Ebbinghaus, Clojure UG Düsseldorf, Twitter: [MrEbbinghaus](https://twitter.com/MrEbbinghaus)
