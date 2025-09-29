package edu.hitsz.matrix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StrassenMatrixMultiplicationTest {
    
    private StrassenMatrixMultiplication strassen;
    
    @BeforeEach void setUp() {
        strassen = new StrassenMatrixMultiplication();
    }
    
    @Test void multiply() {
        // 测试 1x1 矩阵乘法
        int[][] A1 = {{2}};
        int[][] B1 = {{3}};
        int[][] expected1 = {{6}};
        int[][] result1 = strassen.multiply(A1, B1);
        assertArrayEquals(expected1, result1);
        
        // 测试 2x2 矩阵乘法
        int[][] A2 = {{1, 2}, {3, 4}};
        int[][] B2 = {{5, 6}, {7, 8}};
        int[][] expected2 = {{19, 22}, {43, 50}}; // 标准矩阵乘法结果
        int[][] result2 = strassen.multiply(A2, B2);
        assertArrayEquals(expected2, result2);
        
        // 测试 4x4 矩阵乘法
        int[][] A3 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] B3 = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}}; // 单位矩阵
        int[][] result3 = strassen.multiply(A3, B3);
        assertArrayEquals(A3, result3); // 任何矩阵乘以单位矩阵等于其本身
    }
    
    @Test void sub() {
        // 测试矩阵减法
        int[][] A = {{5, 8}, {3, 6}};
        int[][] B = {{2, 3}, {1, 4}};
        int[][] expected = {{3, 5}, {2, 2}};
        int[][] result = strassen.sub(A, B);
        assertArrayEquals(expected, result);
    }
    
    @Test void add() {
        // 测试矩阵加法
        int[][] A = {{1, 2}, {3, 4}};
        int[][] B = {{5, 6}, {7, 8}};
        int[][] expected = {{6, 8}, {10, 12}};
        int[][] result = strassen.add(A, B);
        assertArrayEquals(expected, result);
    }
    
    @Test void split() {
        // 测试分割矩阵
        int[][] P = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[][] C = new int[2][2];
        
        // 分割左上角部分
        strassen.split(P, C, 0, 0);
        int[][] expected1 = {{1, 2}, {5, 6}};
        assertArrayEquals(expected1, C);
        
        // 分割右下角部分
        strassen.split(P, C, 2, 2);
        int[][] expected2 = {{11, 12}, {15, 16}};
        assertArrayEquals(expected2, C);
    }
    
    @Test void join() {
        // 测试合并矩阵
        int[][] C = {{1, 2}, {3, 4}};
        int[][] P = new int[4][4];
        
        // 将C合并到P的左上角
        strassen.join(C, P, 0, 0);
        int[][] expected = {{1, 2, 0, 0}, {3, 4, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        assertArrayEquals(expected, P);
    }
}
