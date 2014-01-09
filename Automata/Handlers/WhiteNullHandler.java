package Automata.Handlers;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Locator;
import CH.ifa.draw.handle.NullHandle;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 28/12/13
 *
 * Copyright Tiago de França Queiroz, 2013.
 *
 * This file is part of Automata.
 *
 * Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */
public class WhiteNullHandler
		extends NullHandle
{
	/**
	 * Initializes the LocatorHandle with the given Locator.
	 * Strategy.
	 */
	public WhiteNullHandler(Figure owner, Locator locator)
	{
		super(owner, locator);
	}

	/**
	 * Draws the NullHandle. NullHandles are drawn as a
	 * red framed rectangle.
	 */
	@Override
	public void draw(Graphics g) {
		Rectangle r = displayBox();

		g.setColor(Color.white);
		g.fillRect(r.x, r.y, r.width, r.height);

		g.setColor(Color.black);
		g.drawRect(r.x, r.y, r.width, r.height);
	}
}
