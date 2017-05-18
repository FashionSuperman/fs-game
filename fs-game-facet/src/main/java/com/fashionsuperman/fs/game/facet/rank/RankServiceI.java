package com.fashionsuperman.fs.game.facet.rank;

import com.fashionSuperman.fs.core.exception.BizException;
import com.fashionsuperman.fs.game.facet.rank.message.MesUpdateUserScore;

public interface RankServiceI {
	public MesUpdateUserScore updateUserScore(MesUpdateUserScore mes) throws BizException;
}
