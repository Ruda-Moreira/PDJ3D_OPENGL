package br.pucpr.mage;

class Uniform {
    private UniformType type;
    private Object value;
    
    public Uniform(UniformType type, Object value) {
        this.type = type;
        this.value = value;
    }
    
    public void set(Shader shader, String name) {
        type.set(shader, name, value);
    }
}