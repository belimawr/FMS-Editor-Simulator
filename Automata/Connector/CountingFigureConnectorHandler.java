package Automata.Connector;

import Automata.Figures.OneConnection;
import CH.ifa.draw.connector.LocatorConnector;
import CH.ifa.draw.framework.ConnectionFigure;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Locator;
import CH.ifa.draw.handle.ConnectionHandle;

import java.awt.*;

/**
 * Author: Tiago de França Queiroz
 * Date: 03/01/14
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
public class CountingFigureConnectorHandler extends ConnectionHandle
{
	private static int BORDER = 4;
	private Color color;
	/**
	 * Constructs a handle with the given owner, locator, and connection prototype
	 */
	public CountingFigureConnectorHandler(Figure owner, Locator l, ConnectionFigure prototype)
	{
		super(owner, l, prototype);

		if(prototype instanceof OneConnection)
			color = Color.blue;
		else
			color = Color.red;
	}

	/*
	 * Add a white border,
	 * so it's easy to see
	 */
	@Override
	public void draw(Graphics g)
	{
		Rectangle r = displayBox();

		r.grow(-BORDER, -BORDER);
		g.setColor(Color.white);

		g.fillOval(r.x, r.y, r.width + BORDER, r.height + BORDER);

		g.setColor(color);
		g.fillOval(r.x + BORDER/2, r.y + BORDER/2, r.width, r.height);
	}

	@Override
	public Rectangle displayBox()
	{
		Rectangle r = super.displayBox();
		r.grow(BORDER, BORDER);

		return r;
	}
}
