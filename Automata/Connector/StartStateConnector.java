package Automata.Connector;

import Automata.Figures.StartState;
import CH.ifa.draw.connector.ChopEllipseConnector;
import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.util.Geom;

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
public class StartStateConnector extends ChopEllipseConnector
{
	public StartStateConnector(Figure owner)
	{
		super(owner);
	}

	@Override
	protected Point chop(Figure target, Point from)
	{
		Rectangle r = ((StartState) target).getCircle().displayBox();
		double angle = Geom.pointToAngle(r, from);
		return Geom.ovalAngleToPoint(r, angle);
	}
}
