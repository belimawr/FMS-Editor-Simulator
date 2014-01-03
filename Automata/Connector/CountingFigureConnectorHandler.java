package Automata.Connector;

import Automata.Figures.OneConnection;
import CH.ifa.draw.framework.*;
import CH.ifa.draw.handle.ConnectionHandle;

import java.awt.*;
import java.util.Enumeration;

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

	private ConnectionFigure createdConnection;

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

	/* Some copy paste with small modifications to allow self connection */
	@Override
	protected Connector findConnectionTarget(int x, int y, Drawing drawing)
	{
		Figure target = findConnectableFigure(x, y, drawing);
		if ((target != null) && target.canConnect()
//				&& !target.includes(owner())
				&& createdConnection.canConnect(owner(), target))
		{
			return findConnector(x, y, target);
		}
		return null;
	}

	/* Copy paste as it is private in super class... */
	private Figure findConnectableFigure(int x, int y, Drawing drawing)
	{
		Enumeration<Figure> k = drawing.figuresReverse();
		while (k.hasMoreElements()) {
			Figure figure = k.nextElement();
			if (!figure.includes(createdConnection) && figure.canConnect())
			{
				if (figure.containsPoint(x, y))
					return figure;
			}
		}
		return null;
	}

	/*
	 * Overriding method to keep a copy of the created
	 * figure as it is private on super class
	 */
	@Override
	protected ConnectionFigure createConnection()
	{
		createdConnection =  (ConnectionFigure) super.createConnection();
		return createdConnection;
	}
}
