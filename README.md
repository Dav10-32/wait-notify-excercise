## Diseño de sincronización

La sincronización del sistema se implementa usando la clase `Control` como un **monitor centralizado**, que actúa como lock compartido para todos los hilos trabajadores. Esto permite coordinar de manera consistente la pausa y reanudación de la ejecución.

La condición de sincronización está representada por la variable booleana `paused`, la cual indica si los hilos pueden continuar o deben detenerse temporalmente. Cada hilo trabajador verifica esta condición a través del método sincronizado `checkPaused()`. Cuando la condición es verdadera, el hilo se bloquea utilizando `wait()`, liberando el lock y evitando el consumo innecesario de CPU.

El hilo controlador es el encargado de modificar el estado de la condición. Para reanudar la ejecución, se utiliza `notifyAll()`, garantizando que todos los hilos en espera sean despertados correctamente. La evaluación de la condición dentro de un ciclo `while` evita problemas de *lost wakeups* y despertares espurios, asegurando una sincronización segura y correcta.

Este diseño elimina el *busy waiting* y asegura una ejecución eficiente y ordenada de los hilos concurrentes.
