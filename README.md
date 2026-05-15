# TP3 - Índice de Estudiantes usando Tabla Hash

## Descripción

Implementación de un módulo de indexación basado en una **Tabla Hash** para el sistema de gestión académica universitaria desarrollado en los TPs anteriores.

El índice permite insertar y buscar estudiantes por su **legajo (DNI)** de forma eficiente, usando una función de dispersión sobre strings y resolución de colisiones con **exploración cuadrática**.

---

## Objetivos

- Implementar una Tabla Hash con todas sus funcionalidades
- Aplicar una función de dispersión (hash) sobre strings
- Resolver colisiones mediante exploración cuadrática
- Respetar un factor de carga máximo de 0.75

---

## Estructura del Proyecto

```
├── Persona.java           # Clase madre con atributos base
├── Estudiante.java        # Hereda de Persona, incluye materias y promedio recursivo
├── Profesor.java          # Hereda de Persona, incluye materias asignadas
├── Personal1.java         # Hereda de Persona, incluye datos laborales
├── Materia.java           # Clase con datos de materia y referencia al profesor
├── Carrera.java           # Gestiona lista de estudiantes por carrera
├── Universidad.java       # Clase principal que integra todas las estructuras
├── indiceEstudiante.java  # Tabla Hash para búsqueda rápida por DNI
└── APP.java               # Main con menú interactivo
```

---

## Implementación de la Tabla Hash

### Parámetros

| Parámetro | Valor |
|-----------|-------|
| Tamaño de la tabla | 23 (número primo) |
| Factor de carga máximo | 0.75 |
| Resolución de colisiones | Exploración cuadrática |

### Función de Dispersión

Suma los valores ASCII de cada carácter del legajo y aplica módulo con el tamaño de la tabla:

```java
private int hash(String legajo) {
    int suma = 0;
    for (int i = 0; i < legajo.length(); i++) {
        suma = suma + legajo.charAt(i);
    }
    return suma % tamaño;
}
```

### Exploración Cuadrática

Cuando hay colisión, la nueva posición se calcula como:

```
posición = (posOriginal + i²) % tamaño
```

Donde `i` es el número de intento.

---

## Tabla de Trabajo

Los legajos utilizados para las pruebas son alfanuméricos. A continuación se muestra el cálculo completo de cada inserción:

### Cálculo de ASCII por legajo

**AB12** → A(65) + B(66) + 1(49) + 2(50) = 230

**ZX90** → Z(90) + X(88) + 9(57) + 0(48) = 283

**LQ33** → L(76) + Q(81) + 3(51) + 3(51) = 259

**MN45** → M(77) + N(78) + 4(52) + 5(53) = 260

**CD78** → C(67) + D(68) + 7(55) + 8(56) = 246

**RS11** → R(82) + S(83) + 1(49) + 1(49) = 263

**GT22** → G(71) + T(84) + 2(50) + 2(50) = 255

### Tabla completa

| Legajo | ASCII total | h(k) = ASCII % 23 | ¿Colisión? | Intentos (i²) | Posición final |
|--------|-------------|-------------------|------------|----------------|----------------|
| AB12   | 230         | 0                 | No         | 0              | 0              |
| ZX90   | 283         | 6                 | No         | 0              | 6              |
| LQ33   | 259         | 6                 | Sí         | 1² = 1         | 7              |
| MN45   | 260         | 7                 | Sí         | 1² = 1         | 8              |
| CD78   | 246         | 14                | No         | 0              | 14             |
| RS11   | 263         | 9                 | No         | 0              | 9              |
| GT22   | 255         | 2                 | No         | 0              | 2              |

### Estado final de la tabla

```
Posición  0  → AB12
Posición  1  → vacía
Posición  2  → GT22
Posición  3  → vacía
Posición  4  → vacía
Posición  5  → vacía
Posición  6  → ZX90
Posición  7  → LQ33  (colisionó con ZX90, saltó 1²=1)
Posición  8  → MN45  (colisionó con LQ33, saltó 1²=1)
Posición  9  → RS11
Posición 10  → vacía
Posición 11  → vacía
Posición 12  → vacía
Posición 13  → vacía
Posición 14  → CD78
Posición 15  → vacía
...
Posición 22  → vacía
```

Factor de carga final: 7/23 = 0.30 ✅ (menor a 0.75)

---

## Menú del Sistema

```
1  - Agregar carrera a la facultad
2  - Agregar estudiante
3  - Cargar una materia a un estudiante
4  - Mostrar carreras de la facultad
5  - Listar alumnos según la carrera
6  - Mostrar alumno por nombre
7  - Agregar un profesor
8  - Ver profesores en la facultad
9  - Agregar materias a profesores
10 - Agregar un empleado de personal
11 - Listar los empleados de personal
12 - Buscar estudiante por DNI (Tabla Hash)
13 - Salir
```

---

## Reflexión

### ¿Dónde hubo más colisiones?
Las colisiones ocurrieron en las posiciones 6, 7 y 8. ZX90 ocupó la posición 6, luego LQ33 colisionó ahí y saltó a la 7, y MN45 colisionó en la 7 y saltó a la 8. Esto sucede porque los legajos LQ33 y ZX90 tienen sumas ASCII que dan el mismo resto al dividir por 23. La zona entre las posiciones 6 y 9 fue la más congestionada.

### ¿Qué tan eficiente fue la exploración cuadrática?
Muy eficiente en este caso. Todas las colisiones se resolvieron en el primer intento (i²=1), lo que significa que ningún elemento necesitó más de un salto para encontrar su posición final. Esto se debe a que el factor de carga fue bajo (0.30), dejando suficiente espacio libre en la tabla para distribuir bien los elementos sin generar clusters grandes.

### ¿Qué pasaría si el tamaño no fuera primo?
Si el tamaño fuera por ejemplo 20 en vez de 23, la distribución sería menos uniforme. Con la exploración cuadrática y un tamaño no primo, algunos índices de la tabla nunca serían alcanzados durante la exploración, lo que provocaría que la tabla rechace inserciones aunque haya posiciones libres disponibles. El tamaño primo garantiza que la exploración cuadrática pueda recorrer todas las posiciones posibles de la tabla.

---

## Tecnologías

- Java
- Programación Orientada a Objetos
- Estructuras de Datos (Tabla Hash)

---

## Autor

Ingeniería en Sistemas de Software
