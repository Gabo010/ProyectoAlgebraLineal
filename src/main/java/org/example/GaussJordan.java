package org.example;

import java.util.Scanner;

public class GaussJordan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de variables: ");
        int numVariables = scanner.nextInt();

        System.out.println("Ingrese la matriz con los coeficientes de las variables:");

        double[][] matriz = new double[numVariables][numVariables + 1];

        for (int i = 0; i < numVariables; i++) {
            for (int j = 0; j < numVariables + 1; j++) {
                System.out.print("Ingrese el elemento A[" + (i + 1) + "][" + (j + 1) + "]: ");
                matriz[i][j] = scanner.nextDouble();
            }
        }

        double[][] solucion = solve(matriz);

        if (solucion == null) {
            System.out.println("Hay variables infinitas en el sistema de ecuaciones....");
        } else {
            System.out.println("La solución es:");
            for (int i = 0; i < numVariables; i++) {
                System.out.println("x" + (i + 1) + " = " + solucion[i][numVariables]);
            }
        }

        scanner.close();
    }

    public static double[][] solve(double[][] matriz) {
        int numFilas = matriz.length;
        int numColumnas = matriz[0].length;

        //Realizar eliminación Gauss-Jordan
        for (int i = 0; i < numFilas; i++) {
            // Encontrar la fila con el pivote más grande
            int pivoteGrande = i;
            for (int j = i + 1; j < numFilas; j++) {
                if (Math.abs(matriz[j][i]) > Math.abs(matriz[pivoteGrande][i])) {
                    pivoteGrande = j;
                }
            }

            // Intercambiar las filas para poner el pivote más grande en la diagonal
            double[] filaTemporal = matriz[i];
            matriz[i] = matriz[pivoteGrande];
            matriz[pivoteGrande] = filaTemporal;

            // Comprobar si el pivote es cero (implica variables infinitas)
            if (matriz[i][i] == 0) {
                // Comprobar si todos los elementos de esta fila también son cero
                boolean convertirCero = true;
                for (int j = i + 1; j < numColumnas; j++) {
                    if (matriz[i][j] != 0) {
                        convertirCero = false;
                        break;
                    }
                }

                if (convertirCero) {
                    return null; // variables infinitas
                } else {
                    continue; // no se puede hacer eliminación Gauss-Jordan en esta columna
                }
            }

            // Dividir la fila pivote por el elemento pivote
            double pivote = matriz[i][i];
            for (int j = i; j < numColumnas; j++) {
                matriz[i][j] /= pivote;
            }

            // Restar múltiplos de la fila pivote de todas las demás filas
            for (int j = 0; j < numFilas; j++) {
                if (j != i) {
                    double factor = matriz[j][i];
                    for (int k = i; k < numColumnas; k++) {
                        matriz[j][k] -= factor * matriz[i][k];
                    }
                }
            }
        }

        return matriz;
    }
}
