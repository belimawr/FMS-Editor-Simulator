package Automata.Decorators;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 04/01/14
 * <p/>
 * Copyright Tiago de França Queiroz, 2014.
 * <p/>
 * This file is part of Automata.Automata.Decorators.
 * <p/>
 * Automata.Automata.Decorators is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * Automata.Automata.Decorators is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with Automata.Automata.Decorators. If not, see <http://www.gnu.org/licenses/>.
 */
public class CurrentStateDecorator extends AutomataDecorator
{
	public CurrentStateDecorator(Figure figure)
	{
		super(figure);
	}

	/*
	 * Returns the atmosphere's size
	 */
	private Point atmosphere_size()
	{
		return new Point(6, 6);
	}

	@Override
	public Rectangle displayBox()
	{
		Rectangle r = fComponent.displayBox();
		r.grow(atmosphere_size().x, atmosphere_size().y);

		return r;
	}

	/*
	 * Draws the atmosphere (magenta border)
	 */
	@Override
	public void draw(Graphics g)
	{
		Rectangle r = displayBox();

		g.setColor(Color.magenta);
		g.fillOval(r.x - atmosphere_size().x/2,
		           r.y - atmosphere_size().y/2,
		           r.width + atmosphere_size().x,
		           r.height + atmosphere_size().y);
		super.draw(g);
	}

	/**
	 * Invalidates the figure extended by its border.
	 */
	@Override
	public void figureInvalidated(FigureChangeEvent e)
	{
		Rectangle rect = e.getInvalidatedRectangle();
		rect.grow(atmosphere_size().x, atmosphere_size().y);
		super.figureInvalidated(new FigureChangeEvent(e.getFigure(), rect));
	}
}
