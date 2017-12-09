# Generate classes
The `json2code.jar` comes from my [`json2code` project](https://github.com/hauke96/json2code). This file will generate code classes from a json scheme file (`orion_scheme.json`).

Just execute a command like the following one:
```
java -jar json2code.jar <SCHEME_FILE> <LANGUAGE> <OUTPUT_PATH> <PACKAGE_NAME>

```
For this server it's:
```
java -jar json2code.jar orion_scheme.json go ../client/src/orion_spur/common/material/ material
```
For this client it's:
```
java -jar json2code.jar orion_scheme.json java ../client/src/orion_spur/common/material/ orion_spur.common.material
```
