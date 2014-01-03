package Automata.Decorators;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 02/01/14
 *
 * Copyright Tiago de França Queiroz, 2014.
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
public class FinalStateDecorator
		extends AutomataDecorator
{
	public FinalStateDecorator(Figure figure)
	{
		super(figure);
	}

	/*
	 * Returns the atmosphere's size
	 */
	private Point border_size()
	{
		return new Point(6, 6);
	}

	@Override
	public Rectangle displayBox()
	{
		Rectangle r = fComponent.displayBox();
		r.grow(border_size().x, border_size().y);

		return r;
	}

	/*
	 * Draws the atmosphere (magenta border)
	 */
	@Override
	public void draw(Graphics g)
	{
		Rectangle r = displayBox();

		g.setColor(Color.black);
		g.drawOval(r.x - border_size().x / 2,
		           r.y - border_size().y / 2,
		           r.width + border_size().x,
		           r.height + border_size().y);
		super.draw(g);
	}

	/**
	 * Invalidates the figure extended by its border.
	 */
	@Override
	public void figureInvalidated(FigureChangeEvent e)
	{
		Rectangle rect = e.getInvalidatedRectangle();
		rect.grow(border_size().x, border_size().y);
		super.figureInvalidated(new FigureChangeEvent(e.getFigure(), rect));
	}
}
