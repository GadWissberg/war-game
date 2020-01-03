package com.gadarts.war.sound;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.gadarts.war.GameAssetManager;
import com.gadarts.war.GameSettings;

public class SoundPlayer {

    public long play(Sound sound) {
        return play(sound, false);
    }

    public long play(Sound sound, boolean loop) {
        return play(sound, loop, null, null);
    }

    public long play(SFX sound) {
        return play(GameAssetManager.getInstance().get(sound.getFileName(), Sound.class));
    }

    public void pauseSound(SFX sound) {
        pauseSound(GameAssetManager.getInstance().get(sound.getFileName(), Sound.class));
    }

    public void pauseSound(Sound sound) {
        pauseSound(sound, -1);
    }

    public void pauseSound(Sound sound, long id) {
        if (id < 0) {
            sound.pause();
        } else {
            sound.pause(id);
        }
    }

    public void resumeSound(Sound sound) {
        resumeSound(sound, -1);
    }

    public void resumeSound(Sound sound, long id) {
        if (id < 0) {
            sound.resume();
        } else {
            sound.resume(id);
        }
    }

    public void resumeSound(SFX sound) {
        resumeSound(GameAssetManager.getInstance().get(sound.getFileName(), Sound.class));
    }

    public long playRandomByDefinitions(SFX sound1, SFX sound2, PerspectiveCamera camera, Vector3 soundSourcePosition) {
        return play(MathUtils.randomBoolean() ? sound1 : sound2, camera, soundSourcePosition);
    }

    private long play(SFX sound, PerspectiveCamera camera, Vector3 soundSourcePosition) {
        return play(GameAssetManager.getInstance().get(sound.getFileName(), Sound.class), camera, soundSourcePosition);
    }

    private long play(Sound sound, PerspectiveCamera camera, Vector3 soundSourcePosition) {
        return play(sound, false, camera, soundSourcePosition);
    }

    private long play(Sound sound, boolean loop, PerspectiveCamera camera, Vector3 soundSourcePosition) {
        if (!GameSettings.ALLOW_SOUND) return -1;
        long id;
        boolean dynamicSound = camera != null && soundSourcePosition != null;
        float volume = dynamicSound ? calculateVolume(camera, soundSourcePosition) : 1f;
        float pan = dynamicSound ? calculatePan(camera, soundSourcePosition) : 0f;
        if (loop) id = sound.loop(volume, 1, pan);
        else id = sound.play(volume, MathUtils.random(0.8f, 1.2f), pan);
        return id;
    }

    private float calculatePan(PerspectiveCamera camera, Vector3 soundSourcePosition) {
        return 0;
    }

    private float calculateVolume(PerspectiveCamera camera, Vector3 soundSourcePosition) {
        float dst = soundSourcePosition.dst(camera.position);
        return MathUtils.norm(0, 2, 100f / (dst * dst));
    }
}
