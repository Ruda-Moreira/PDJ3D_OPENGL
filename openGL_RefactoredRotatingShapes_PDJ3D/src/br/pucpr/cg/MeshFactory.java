package br.pucpr.cg;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;
import org.joml.Vector3f;

public class MeshFactory {
    
    public static Mesh createCube() {
        return createCube(
            new Vector3f(0.988f, 0.663f, 0.522f),
            new Vector3f(0.522f, 0.792f, 0.365f),
            new Vector3f(0.459f, 0.537f, 0.749f),
            new Vector3f(0.976f, 0.549f, 0.714f),
            new Vector3f(0.647f, 0.537f, 0.757f),
            new Vector3f(0.753f, 0.729f, 0.600f)
        );
    }   
    
    public static Mesh createCube(Vector3f color) {
        return createCube(color, color, color, color, color, color);
    }
    
    public static Mesh createCube(Vector3f frontColor, Vector3f backColor, Vector3f topColor, Vector3f bottomColor, Vector3f rightColor, Vector3f leftColor) {
        return new MeshBuilder()
        .addVector3fAttribute("aPosition", 
            //Face próxima
             -0.5f,  0.5f,  0.5f,  //0
              0.5f,  0.5f,  0.5f,  //1
             -0.5f, -0.5f,  0.5f,  //2
              0.5f, -0.5f,  0.5f,  //3
            //Face afastada
             -0.5f,  0.5f, -0.5f,  //4
              0.5f,  0.5f, -0.5f,  //5
             -0.5f, -0.5f, -0.5f,  //6
              0.5f, -0.5f, -0.5f,  //7
            //Face superior
             -0.5f,  0.5f,  0.5f,  //8
              0.5f,  0.5f,  0.5f,  //9
             -0.5f,  0.5f, -0.5f,  //10
              0.5f,  0.5f, -0.5f,  //11
            //Face inferior
             -0.5f, -0.5f,  0.5f,  //12
              0.5f, -0.5f,  0.5f,  //13
             -0.5f, -0.5f, -0.5f,  //14
              0.5f, -0.5f, -0.5f,  //15 
            //Face direita
              0.5f, -0.5f,  0.5f,  //16
              0.5f,  0.5f,  0.5f,  //17
              0.5f, -0.5f, -0.5f,  //18
              0.5f,  0.5f, -0.5f,  //19
            //Face esquerda
             -0.5f, -0.5f,  0.5f,   //20
             -0.5f,  0.5f,  0.5f,   //21
             -0.5f, -0.5f, -0.5f,  //22
             -0.5f,  0.5f, -0.5f)  //23
        .addVector3fAttribute("aColor",
            //Face próxima
              frontColor, 
              frontColor,
              frontColor,
              frontColor,
            //Face afastada
              backColor, 
              backColor, 
              backColor, 
              backColor, 
            //Face superior
              topColor, 
              topColor, 
              topColor, 
              topColor, 
            //Face inferior
              bottomColor,
              bottomColor,
              bottomColor,
              bottomColor,
            //Face direita
              rightColor, 
              rightColor, 
              rightColor, 
              rightColor, 
            //Face esquerda
              leftColor, 
              leftColor,
              leftColor,
              leftColor)
        .setIndexBuffer(
            //Face próxima
              0,  2,  3,
              0,  3,  1,
            //Face afastada
              4,  7,  6,
              4,  5,  7,
            //Face superior
              8, 11, 10,
              8,  9, 11,
            //Face inferior
             12, 14, 15,
             12, 15, 13,
            //Face direita
             16, 18, 19,
             16, 19, 17,
            //Face esquerda
             20, 23, 22,
             20, 21, 23)
        .loadShader("/br/pucpr/resource/basic")
        .create();        
    }

    public static Mesh createSquare(){
        return new MeshBuilder()
                .addVector3fAttribute("aPosition",
                        -0.5f,  0.5f, 0.0f,
                        0.5f,  0.5f, 0.0f,
                        -0.5f, -0.5f, 0.0f,
                        0.5f,  -0.5f, 0.0f)
                .addVector3fAttribute("aColor",
                        1.0f, 0.0f, 0.0f,
                        1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 1.0f)
                .setIndexBuffer(
                        0, 2, 3,
                        0, 3, 1)
                .loadShader("/br/pucpr/resource/basic")
                .create();
    }

    public static Mesh createTriangle(){
        return new MeshBuilder()
                .addVector3fAttribute("aPosition",
                        0.0f,  0.5f, 0.0f,
                        -0.5f, -0.5f, 0.0f,
                        0.5f, -0.5f, 0.0f)
                .addVector3fAttribute("aColor",
                        1.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 1.0f)
                .loadShader("/br/pucpr/resource/basic")
                .create();
    }

    public static Mesh createPentagon(){
        return new MeshBuilder()
                .addVector3fAttribute("aPosition",
                        0.00f,  0.4f, 0.0f,//1
                        -0.50f,  0.00f, 0.0f,//2
                        0.50f,  0.00f, 0.0f,//3
                        -0.25f, -0.60f,0.0f,//4
                        0.25f, -0.60f,0.0f)//5
                .addVector3fAttribute("aColor",
                        1.0f, 0.0f, 0.0f,
                        1.0f, 1.0f, 1.0f,
                        0.0f, 1.0f, 0.0f,
                        0.0f, 0.0f, 1.0f,
                        0.0f, 0.0f, 0.5f)
                .setIndexBuffer(
                        0, 1, 2,
                        3, 2, 1,
                        3, 4, 2)
                .loadShader("/br/pucpr/resource/basic")
                .create();
    }

}
