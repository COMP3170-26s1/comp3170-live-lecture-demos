#version 410

in vec4 v_colour;	// RGBA

layout(location = 0) out vec4 o_colour;	// RGBA

void main() {
	vec3 c = v_colour.rgb;
	
//	c = round(c * 10) / 10;
	c = normalize(c);
	
    o_colour = vec4(c, 1);
}

