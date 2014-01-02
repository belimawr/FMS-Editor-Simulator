package Automata.Figures;

import Automata.Handlers.WhiteNullHandler;
import CH.ifa.draw.figure.EllipseFigure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.locator.RelativeLocator;

import java.awt.*;
import java.util.Vector;

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
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with Automata If not, see <http://www.gnu.org/licenses/>.
 */
public class CountingFigure
		extends EllipseFigure
{
	private Rectangle display_box;
	static int counter = 0;
	private int my_number;
	private boolean state;

	public CountingFigure(boolean state)
	{
		super();
		this.state = state;
		my_number = counter++;
		if(state)
			setAttribute("FillColor", Color.blue);
		else
			setAttribute("FillColor", Color.red);
	}

	/**
	 * Translation method.
	 *
	 * @param x
	 * @param y
	 */
	@Override
	protected void basicMoveBy(int x, int y)
	{
		display_box.translate(x, y);
	}

	/**
	 * Returns a copy of its display box
	 *
	 * @return
	 */
	@Override
	public Rectangle displayBox()
	{
		return new Rectangle(
				display_box.x,
				display_box.y,
				display_box.width,
				display_box.height);
	}

	/**
	 * Fix the size of the state
	 *
	 * @param origin Origin of the display box
	 * @param corner Opposite corner of the display box
	 */
	@Override
	public void basicDisplayBox(Point origin, Point corner)
	{
		display_box = new Rectangle(origin);

		display_box.setSize(65, 65);
	}

	@Override
	public Vector<Handle> handles()
	{
		Vector<Handle> handles = new Vector<Handle>();
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.southEast()));
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.northEast()));
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.southWest()));
		handles.addElement(new WhiteNullHandler(this, RelativeLocator.northWest()));
		return handles;
	}

	public String toString()
	{
		if(state)
			return String.format("%d-OneState", my_number);
		else
			return String.format("%d-ZeroState", my_number);
	}
}
