package Automata.Figures;

import Automata.Handlers.WhiteNullHandler;
import CH.ifa.draw.connector.ChopEllipseConnector;
import CH.ifa.draw.figure.CompositeFigure;
import CH.ifa.draw.figure.EllipseFigure;
import CH.ifa.draw.framework.Connector;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.locator.RelativeLocator;

import java.awt.*;
import java.util.Vector;

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
public class EndState extends CompositeFigure
{
	private static final int border_size = 8;
	private CountingFigure circle;
	private EllipseFigure border;

	public EndState(CountingFigure figure)
	{
		circle = figure;
		border = new EllipseFigure();
		border.setAttribute("FillColor", Color.GRAY);

		Rectangle r = circle.displayBox();

		r.grow(border_size, border_size);

		Point start = r.getLocation();
		Point end = new Point(start.x + r.width, start.y + r.height);

		border.basicDisplayBox(start, end);

		add(border);
		add(circle);
	}
	@Override
	public void basicDisplayBox(Point origin, Point corner)
	{
		circle.basicDisplayBox(origin, corner);

		Rectangle r = circle.displayBox();

		r.grow(border_size, border_size);

		Point start = r.getLocation();
		Point end = new Point(start.x + r.width, start.y + r.height);

		border.basicDisplayBox(start, end);
	}

	@Override
	public Rectangle displayBox()
	{
		return border.displayBox();
	}

	@Override
	public Connector connectorAt(int x, int y)
	{
		return new ChopEllipseConnector(this);
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
}
