package Automata.Decorators;

import Automata.Model.FSM_Model;
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
public class StartStateDecorator extends AutomataDecorator
{
	public StartStateDecorator(Figure figure)
	{
		super(figure);
		/* Add model as listener*/
		this.addFigureChangeListener(FSM_Model.getInstance());
	}

	@Override
	public Rectangle displayBox()
	{
		Rectangle rect = fComponent.displayBox();
		Point center = fComponent.center();
		Point p1, p2;
		int radius = rect.height/2;

		p1 = new Point(center.x, center.y - radius);
		p2 = new Point(p1.x, p1.y - 50);
		rect.add(p1);
		rect.add(p2);
		return rect;
	}

	@Override
	public void draw(Graphics g)
	{
		Rectangle r = fComponent.displayBox();
		Polygon triangle = new Polygon();
		Point p1, p2, p3;
		Point center = fComponent.center();
		int radius = r.height/2;

		p1 = new Point(center.x, center.y - radius);
		p2 = new Point(p1.x + 10, p1.y - 15);
		p3 = new Point(p1.x - 10, p1.y - 15);

		triangle.addPoint(p1.x, p1.y);
		triangle.addPoint(p2.x, p2.y);
		triangle.addPoint(p3.x, p3.y);
		g.setColor(Color.black);
		g.fillPolygon(triangle);
		g.drawLine(p1.x, p1.y, p1.x, p1.y - 50);
		super.draw(g);
	}

	/**
	 * Invalidates the figure extended by its decorator.
	 */
	@Override
	public void figureInvalidated(FigureChangeEvent e)
	{
		Rectangle rect = e.getInvalidatedRectangle();
		Point center = fComponent.center();
		Point p1, p2;
		int radius = rect.height/2;

		p1 = new Point(center.x, center.y - radius);
		p2 = new Point(p1.x, p1.y - 50);
		rect.add(p1);
		rect.add(p2);
		super.figureInvalidated(new FigureChangeEvent(e.getFigure(), rect));
	}
}
