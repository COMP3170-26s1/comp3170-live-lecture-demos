#version 410

uniform vec3 u_colour;			// colour as a 3D vector (r,g,b)
uniform vec2 u_screenSize;  	// screen dimensions in pixels

layout(location = 0) out vec4 o_colour;	// output to colour buffer

void main() {
   vec2 p = gl_FragCoord.xy / u_screenSize;   // scale p into range (0,0) to (1,1)
   float d1 = (p.y - p.x);
   float d2 = (p.x - p.y);
   o_colour = vec4(u_colour.brg, 1);
//   o_colour = vec4(u_colour.r + d1, u_colour.g + d2, u_colour.b, 1);
}
