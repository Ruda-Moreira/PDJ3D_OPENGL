package br.pucpr.mage;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Bruno on 22/05/2017.
 */
public class TextureParameters {
    private int minFilter;
    private int magFilter;
    private int wrapS;
    private int wrapT;

    public TextureParameters(int minFilter, int magFilter,
                             int wrapS, int wrapT) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
    }

    public TextureParameters(int minFilter, int magFilter, int wrapS) {
        this(minFilter, magFilter, wrapS, wrapS);
    }
    public TextureParameters(int minFilter, int wrapS) {
        this(minFilter, GL_LINEAR, wrapS);
    }
    public TextureParameters(int minFilter) {
        this(minFilter, GL_REPEAT);
    }
    public TextureParameters() {
        this(GL_LINEAR_MIPMAP_LINEAR);
    }

    public int getMinFilter() {
        return minFilter;
    }
    public int getMagFilter() {
        return magFilter;
    }
    public int getWrapS() {
        return wrapS;
    }
    public int getWrapT() {
        return wrapT;
    }

    public boolean isMipMapped()	{
        return minFilter	==	GL_NEAREST_MIPMAP_NEAREST	||
                minFilter	==	GL_LINEAR_MIPMAP_NEAREST	||
                minFilter	==	GL_NEAREST_MIPMAP_LINEAR	||
                minFilter	==	GL_LINEAR_MIPMAP_LINEAR;
    }

    public void	apply(int target)	{
        glTexParameteri(target,	GL_TEXTURE_MIN_FILTER,	minFilter);
        glTexParameteri(target,	GL_TEXTURE_MAG_FILTER,	magFilter);
        glTexParameteri(target,	GL_TEXTURE_WRAP_S,	wrapS);
        glTexParameteri(target,	GL_TEXTURE_WRAP_T,	wrapT);
    }

}