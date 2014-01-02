package Automata.Figures;

import Automata.Handlers.WhiteNullHandler;
import CH.ifa.draw.figure.ArrowTip;
import CH.ifa.draw.figure.CompositeFigure;
import CH.ifa.draw.figure.LineFigure;
import CH.ifa.draw.framework.Handle;
import CH.ifa.draw.locator.RelativeLocator;

import java.awt.*;
import java.util.Vector;

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
public class StartState extends CompositeFigure
{

	private CountingFigure circle;
	private LineFigure line;

	public StartState(boolean state)
	{
		line = new LineFigure();
		circle = new CountingFigure(state);
		line.setStartDecoration(new ArrowTip());
		add(line);
		add(circle);
	}

	public StartState(CountingFigure figure)
	{
		Rectangle ret = figure.displayBox();
		line = new LineFigure();
		circle = figure;

		line.setStartDecoration(new ArrowTip());
		Point start = new Point(circle.center().x, circle.center().y - ret.height/2);
		Point end  = new Point(start.x, start.y - ret.height/2);

		line.basicDisplayBox(start, end);
		add(line);
		add(circle);
	}

	@Override
	public void basicDisplayBox(Point origin, Point corner)
	{
		System.out.printf("StartState: (%d, %d; %d, %d)\n", origin.x, origin.y, corner.x, corner.y);
		circle.basicDisplayBox(origin, corner);
		Rectangle ret = circle.displayBox();
		Point start, end;

		start = new Point(circle.center().x, circle.center().y - ret.height/2);
		end  = new Point(start.x, start.y - ret.height/2);

		line.basicDisplayBox(start, end);
	}

	@Override
	public Rectangle displayBox()
	{
		Rectangle ret = circle.displayBox();
		Point start, end;

		start = new Point(circle.center().x, circle.center().y - ret.height/2);
		end  = new Point(start.x, start.y - ret.height/2);

		ret.add(end);
		return ret;
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
