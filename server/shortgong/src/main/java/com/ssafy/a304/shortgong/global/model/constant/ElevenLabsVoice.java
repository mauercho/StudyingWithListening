package com.ssafy.a304.shortgong.global.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ElevenLabsVoice {
	// https://elevenlabs.io/docs/api-reference/get-voices
	ARIA("9BWtsMINqrJLrRacOk9x"),
	ROGER("CwhRBWXzGAHq8TQ4Fs17"),
	SARAH("EXAVITQu4vr4xnSDxMaL"),
	LAURA("FGY2WhTYpPnrIDTdsKH5"),
	CHARLIE("IKne3meq5aSn9XLyUdCD"),
	GEORGE("JBFqnCBsd6RMkjVDRZzb"),
	CALLUM("N2lVS1w4EtoT3dr4eOWO"),
	ALICE("Xb7hH8MSUJpSbSDYk0k2"),
	BRIAN("nPczCjzI2devNBz1zQrb");

	private final String voiceId;
}
