#version 410

layout(location = 0) out vec4 o_colour;	// BRIGHTNESS

uniform vec3 u_diffuseMaterial;		// RGB colour of surface	- BRIGHTNESS
uniform vec3 u_specularMaterial;	// RGB colour of surface	- BRIGHTNESS
uniform float u_shininess;			// Phong exponent

uniform vec3 u_ambientIntensity;	// RGB colour of ambient light	- INTENSITY
uniform vec3 u_lightIntensity;		// RGB colour of direct light	- INTENSITY
uniform vec4 u_lightDirection; 		// direction to light source vector WORLD

uniform vec4 u_cameraPosition; 		// position of camera WORLD

in vec4 v_position; 				// WORLD
in vec4 v_normal; 					// WORLD

const vec3 GAMMA = vec3(2.2);

void main() {
	vec4 s = normalize(u_lightDirection);
	vec4 n = normalize(v_normal);
	vec4 r = -reflect(s,n);
	vec4 v = normalize(u_cameraPosition - v_position);

	vec3 diffuseMaterialIntensity = pow(u_diffuseMaterial, GAMMA); 
	vec3 specularMaterialIntensity = pow(u_specularMaterial, GAMMA);; 

	vec3 ambient = u_ambientIntensity * diffuseMaterialIntensity;
	vec3 diffuse = u_lightIntensity * diffuseMaterialIntensity * max(0, dot(s, n));
	vec3 specular = vec3(0);
	
	// only light surfaces that are facing the light source
	if (dot(n,s) > 0) {
		specular = u_lightIntensity * specularMaterialIntensity * pow(max(0, dot(r, v)), u_shininess);		
	}
	
	vec3 intensity = ambient + diffuse + specular; 
	vec3 brightness = pow(intensity, 1. / GAMMA);
	
    o_colour = vec4(brightness,1);
}

