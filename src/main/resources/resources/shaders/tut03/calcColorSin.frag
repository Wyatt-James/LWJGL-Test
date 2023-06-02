#version 330

out vec4 outputColor;

uniform float fragLoopDuration;
uniform float time;

const vec4 firstColor = vec4(1.0f, 1.0f, 1.0f, 1.0f);
const vec4 secondColor = vec4(1.0f, 0.0f, 0.0f, 1.0f);

void main()
{
    float sinTime = sin(time * 3.14159f / fragLoopDuration);
	float currLerp = abs(sinTime);
	
	outputColor = mix(firstColor, secondColor, currLerp);
}
