#version 410

in vec4 a_position;	/* vertex position */
uniform mat4 u_modelMatrix; /* translation/rotation/scaling matrix */

void main() {
//	gl_Position = a_position;
    gl_Position = u_modelMatrix * a_position;
}
