package logger

import (
	"fmt"
	"os"
	"runtime"
	"runtime/debug"
	"strconv"
	"strings"
	"time"
)

var DebugMode = false
var TestMode = false

var FMT_STRING string = "%-7s %-19s %s: %s\n"

func Debug(message string) {
	if !DebugMode || TestMode {
		return
	}

	os.Stdout.WriteString(fmt.Sprintf(FMT_STRING, "[debug]", getTimeString(), getCallerName()+"() at "+strconv.Itoa(getCallerLine()), message))
}

func Info(message string) {
	if TestMode {
		return
	}

	os.Stdout.WriteString(fmt.Sprintf(FMT_STRING, "[info]", getTimeString(), getCallerName(), message))
}

func Error(message string) {
	if TestMode {
		return
	}

	os.Stderr.WriteString(fmt.Sprintf(FMT_STRING, "[ERROR]", getTimeString(), getCallerName()+"() at "+strconv.Itoa(getCallerLine()), message))
}

func Fatal(message string) {
	os.Stderr.WriteString(fmt.Sprintf("\n\n"+FMT_STRING+"\n\n", "[FATAL]", getTimeString(), getCallerName()+"() at "+strconv.Itoa(getCallerLine()), message))
	debug.PrintStack()
	Plain("\n\nAhhh, *urg*, I'm sorry but there was a really bad error inside of me. Above the stack trace is a message marked with [FATAL], you'll find some information there.\n\nI hope my death ... eh ... crash is only an exception and will be fixed soon ... my power ... leaves me ... good bye ... x.x")
	os.Exit(1)
}

func Plain(message string) {
	if TestMode {
		return
	}

	os.Stdout.WriteString(message + "\n")
}

func getCallerName() string {
	pc, _, _, _ := runtime.Caller(2)
	path := runtime.FuncForPC(pc).Name()
	splittedPath := strings.Split(path, "/")
	fileName := splittedPath[len(splittedPath)-1]
	return fileName
}

func getCallerLine() int {
	_, _, line, _ := runtime.Caller(2)
	return line
}

func getTimeString() string {
	return time.Now().Format(time.RFC822)
}
