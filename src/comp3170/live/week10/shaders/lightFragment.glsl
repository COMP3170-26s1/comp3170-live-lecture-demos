#version 410

layout(location = 0) out vec4 o_colour;

uniform vec3 u_diffuseMaterial;		// RGB colour of surface
uniform vec3 u_ambientIntensity;	// RGB colour of ambient light
uniform vec3 u_lightIntensity;		// RGB colour of direct light
uniform vec4 u_lightDirection; 		// direction light source vector WORLD

in vec4 v_normal; // WORLD

void main() {
	vec4 s = normalize(u_lightDirection);
	vec4 n = normalize(v_normal);

	vec3 ambient = u_ambientIntensity * u_diffuseMaterial;
	vec3 diffuse = u_lightIntensity * u_diffuseMaterial * max(0, dot(s, n));
	vec3 specular = vec3(0);
	
	vec3 light = ambient + diffuse + specular; 
    o_colour = vec4(light,1);
}

