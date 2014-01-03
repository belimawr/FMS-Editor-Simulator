package Automata.Tools;

import CH.ifa.draw.framework.*;
import CH.ifa.draw.tool.ConnectionTool;

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
 *
 * You should have received a copy of the GNU General Public License
 * along with Automata. If not, see <http://www.gnu.org/licenses/>.
 */

public class FSM_ConnectionTool extends ConnectionTool
{
	public FSM_ConnectionTool(DrawingView view, ConnectionFigure prototype)
	{
		super(view, prototype);
	}

	/*
	 * Allows self connection
	 */
	@Override
	protected Figure findTarget(int x, int y, Drawing drawing)
	{
		Figure target = findConnectableFigure(x, y, drawing);
		Figure start = getStartConnector().owner();

		if (target != null
				&& createdFigure() != null
				&& target.canConnect()
//				&& !target.includes(start)
				&& createdFigure().canConnect(start, target))
			return target;
		return null;
	}
}
