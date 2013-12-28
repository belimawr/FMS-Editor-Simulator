package Automata.Decorators;

import Automata.Figures.CountingFigure;
import CH.ifa.draw.figure.DecoratorFigure;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.FigureChangeEvent;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 21/12/13
 * <p/>
 * Copyright Tiago de França Queiroz, 2013.
 * <p/>
 * This file is part of Automata
 * <p/>
 * Automata is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * Automata is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with Automata If not, see <http://www.gnu.org/licenses/>.
 */
public class StartStateDecorator
		extends DecoratorFigure
{
	public StartStateDecorator(Figure figure)
	{
		super(figure);
	}

	/*
	 * Returns the size of the border
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

	public Figure getFigure()
	{
		return fComponent;
	}
}
