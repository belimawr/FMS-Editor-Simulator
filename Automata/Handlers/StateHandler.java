package Automata.Handlers;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.handle.LocatorHandle;
import CH.ifa.draw.locator.RelativeLocator;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
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
 * along with Automata If not, see <http://www.gnu.org/licenses/>.
 */
public class StateHandler extends LocatorHandle
{
	/**
	 * This method is called every time that the handle is
	 * moved and it is responsible by changing the size of its
	 * figure.
	 *
	 * The anchors are the point where the user clicked, it's
	 * ignored.
	 *
	 * @param x the current x position
	 * @param y the current y position
	 * @param anchorX the x position where the interaction started
	 * @param anchorY the y position where the interaction started
	 * @param view
	 */
	@Override
	public void invokeStep(int x, int y, int anchorX, int anchorY, DrawingView view)
	{
		Rectangle r = owner().displayBox();

		/*
		 * Calls displayBox passing the beginning and
		 * end of a square. As the handle is in the middle
		 * of the planet/circle, the circle's radius has to
		 * be added to X and Y coordinates.
		 */
		owner().displayBox(
				new Point(r.x, r.y),
				new Point(Math.max(x, r.x) + r.width / 2, Math.max(y, r.y) + r.height / 2));
	}

	/**
	 * Initializes the LocatorHandle with the given Locator.
	 * Strategy.
	 */
	public StateHandler(Figure owner)
	{
		super(owner, RelativeLocator.center());
	}
}
